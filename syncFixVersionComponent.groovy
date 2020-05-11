import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.search.SearchProvider
import com.atlassian.jira.jql.parser.JqlQueryParser
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.jira.project.version.Version
import com.atlassian.jira.bc.project.component.ProjectComponent
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.event.type.EventDispatchOption
import com.atlassian.jira.issue.comments.Comment

def userManager = ComponentAccessor.getUserManager()
def jqlQueryParser = ComponentAccessor.getComponent(JqlQueryParser)
def searchProvider = ComponentAccessor.getComponent(SearchProvider)
def issueManager = ComponentAccessor.getIssueManager()
def commentManager = ComponentAccessor.getCommentManager()
def user = userManager.getUserByName("automation")

//JQL Query 
def query = jqlQueryParser.parseQuery("issuetype in ('SAMPLE') AND project = 'SAMPLE'") 
def results = searchProvider.search(query, user, PagerFilter.getUnlimitedFilter())
def issues = results.getIssues()

List issueKeys = []
issues.each { issue ->
	issueKeys.add(issue.getKey())
    MutableIssue mutableIssue = issueManager.getIssueObject(issue.getId())
    
    //Get parent's fix versions and components
    Issue parentIssue = issue.getParentObject()
    log.warn("Parent Issue: " + parentIssue.getKey())
    Collection<Version> parentFixVersions = new ArrayList<Version>()
    Collection<ProjectComponent> parentComponents = new ArrayList<ProjectComponent>()

    parentFixVersions = parentIssue.getFixVersions()
    parentComponents = parentIssue.getComponents()

	Collection<ProjectComponent> childComponents = issue.getComponents()
	List componentNames = []
	childComponents.each{ component->
        componentNames.add(component.name)
    }

	List parentComponentNames = []
	parentComponents.each{ component->
        parentComponentNames.add(component.name)
    }
    //Sync parent's fix version and components to the subtasks
    mutableIssue.setFixVersions(parentFixVersions)
    mutableIssue.setComponent(parentComponents)
    issueManager.updateIssue(user, mutableIssue, EventDispatchOption.ISSUE_UPDATED, false)
}
log.warn(issueKeys)
