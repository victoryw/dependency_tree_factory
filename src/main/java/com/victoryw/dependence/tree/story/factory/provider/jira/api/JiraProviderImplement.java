package com.victoryw.dependence.tree.story.factory.provider.jira.api;

import com.victoryw.dependence.tree.story.factory.domain.MethodCallTreeNodeGroup;
import com.victoryw.dependence.tree.story.factory.provider.jira.api.dto.Fields;
import com.victoryw.dependence.tree.story.factory.provider.jira.api.dto.Issuetype;
import com.victoryw.dependence.tree.story.factory.provider.jira.api.dto.JiraCardDto;
import com.victoryw.dependence.tree.story.factory.provider.jira.api.dto.Project;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Queue;
import java.util.stream.Collectors;

public class JiraProviderImplement {

    private JiraApiClient jiraApiClient;

    public JiraProviderImplement() {
        jiraApiClient = JiraApiClient.create();
    }

    public void createIssues(Queue<MethodCallTreeNodeGroup> nodeGroups) {
        nodeGroups.forEach(this::createIssue);
    }

    private void createIssue(MethodCallTreeNodeGroup nodeGroup) {
        Project project = new Project();
        project.setKey("LPJO");
        Issuetype issuetype = new Issuetype();
        issuetype.setId("10100");
        Fields fields = new Fields();
        fields.setProject(project);
        fields.setIssuetype(issuetype);
        fields.setSummary(toSummary(nodeGroup));
        fields.setDescription(toDescription(nodeGroup));
        JiraCardDto dto = new JiraCardDto();
        dto.setFields(fields);
        final Call call = jiraApiClient.createIssues(dto);
        final Response response = Try.of(call::execute).get();
        if(! response.isSuccessful()) {
            throw new RuntimeException(response.message());
        }
    }

    private String toSummary(MethodCallTreeNodeGroup nodeGroup) {
        final String sourceTitle = nodeGroup.getSplitFrom().data().getTitle();
        final String targetsTitle = nodeGroup.getNodes().stream().limit(3).map(group ->
                group.data().getTitle()).collect(Collectors.joining(","));

        return String.format("[新契约]从 %s 解离出 %s ...", sourceTitle,targetsTitle);
    }

    private String toDescription(MethodCallTreeNodeGroup nodeGroup) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(String.format("移除%s对以下节点的依赖 \\r\\n", nodeGroup.getSplitFrom()));
        nodeGroup.getNodes().forEach(
                node -> {
                    buffer.append(String.format("%s\\r\\n", node.data().getTitle()));
                }
        );

        return buffer.toString();
    }
}
