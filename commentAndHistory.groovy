import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager
import com.atlassian.jira.issue.changehistory.ChangeHistoryItem
import com.atlassian.jira.issue.comments.Comment

// Get issue
def issueManager = ComponentAccessor.getIssueManager()
Issue issue = issueManager.getIssueObject("SAMPLE-123")
def userManager = ComponentAccessor.getUserManager()
def commentManager = ComponentAccessor.getCommentManager()

ChangeHistoryManager changeHistoryManager = ComponentAccessor.getChangeHistoryManager()

def user = userManager.getUserByName("automation")

// add the comment
def comment = "What you want to comment"
commentManager.create(issue, user, comment, null, null, new Date(), properties, true)

List<ChangeHistoryItem> changeItems = changeHistoryManager.getAllChangeItems(issue)
for (item in changeItems)
{
    log.warn("Field: " + item.field + " Timestamp: " + item.created + " OG Value: " + item.fromValue + " toValue: " + item.toValue + " User: " + item.userKey)
}
