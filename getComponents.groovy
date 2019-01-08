import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.bc.project.component.ProjectComponent

def componentList = []
Collection<ProjectComponent> components = new ArrayList<ProjectComponent>()
components = issue.getComponents()
components.each{ c->
    componentList.add(c.getName())
}
