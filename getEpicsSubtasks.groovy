import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.link.IssueLink
import com.atlassian.jira.issue.link.IssueLinkManager

import com.atlassian.jira.issue.search.SearchProvider
import com.atlassian.jira.jql.parser.JqlQueryParser
import com.atlassian.jira.web.bean.PagerFilter

def issueLinkManager = ComponentAccessor.getIssueLinkManager()
def issueManager = ComponentAccessor.getIssueManager()
def jqlQueryParser = ComponentAccessor.getComponent(JqlQueryParser)
def searchProvider = ComponentAccessor.getComponent(SearchProvider)
def userManager = ComponentAccessor.getUserManager()

def user = userManager.getUserByName("automation")
def query = jqlQueryParser.parseQuery("project = SAMPLE AND issuetype = Epic ORDER BY key ASC")
def results = searchProvider.search(query, user, PagerFilter.getUnlimitedFilter())
def issues = results.getIssues()
log.warn("Total Epic issues: ${results.total}")

def totalMP = 0
def comment = ''<<''
issues.each{ epicIssue->
    Issue e = issueManager.getIssueObject(epicIssue.getId())
    List<IssueLink> links = issueLinkManager.getOutwardLinks(e.getId())
	Collection<Issue> issuesInEpic = new ArrayList<Issue>()
	links.each{ link->
		String name = link.getIssueLinkType().getName()
		Issue destinationObject = link.getDestinationObject()
		if (name.equals("Epic-Story Link")){ 
            issuesInEpic.add(destinationObject)
            def subtasks = destinationObject.getSubTaskObjects()
            totalMP++
        }
	}
    comment << "Epic: ${e.getKey()}\n"
    issuesInEpic.each{ i->
        def subtasks = i.getSubTaskObjects()
        comment << "SAMPLE PROJECT (issues in epic): ${i}, Subtasks: ${subtasks}\n"
    }
}
log.warn(comment)
log.warn("Total SAMPLE PROJECT Issues: ${totalMP}")
