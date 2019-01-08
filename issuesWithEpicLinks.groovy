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
def query = jqlQueryParser.parseQuery("project = SAMPLE AND issuetype = 'SAMPLE' AND 'Epic Link' is not EMPTY ORDER BY key ASC")
def results = searchProvider.search(query, user, PagerFilter.getUnlimitedFilter())
def issues = results.getIssues()
def total = results.total

//Issues with epic links
def comment = ''<<''
issues.each{
    comment << "${it.getKey()}, "
}
log.warn(comment)
log.warn(total)
