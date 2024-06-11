package com.xws111.sqlpractice.provider;

import com.xws111.sqlpractice.model.dto.question.QuestionListRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.stream.Collectors;

/**
 * @Description:
 * @Date 2024/6/11 17:39
 * @Version 1.0
 * @Author xg
 */
public class QuestionSqlProvider {
    public String getQuestionAllVOByRequest(QuestionListRequest request) {
        SQL sql = new SQL() {
            {
                SELECT("q.*, GROUP_CONCAT(t.name SEPARATOR ', ') as tagList");
                FROM("question q");
                JOIN("question_tag qt ON q.id = qt.question_id");
                JOIN("tag t ON qt.tag_id = t.id");
                if (request.getId() != null && request.getId() > 0) {
                    WHERE("q.id = #{id}");
                }
                if (StringUtils.isNotBlank(request.getTitle())) {
                    WHERE("q.title LIKE #{title}");
                }
                if (StringUtils.isNotBlank(request.getContent())) {
                    WHERE("q.content LIKE #{content}");
                }
                if (request.getTags() != null && !request.getTags().isEmpty()) {
                    String tagNames = request.getTags().stream()
                            .map(tag -> "'" + tag + "'")
                            .collect(Collectors.joining(", "));
                    WHERE("q.id IN (" +
                            "SELECT qt.question_id FROM question_tag qt " +
                            "JOIN tag t ON qt.tag_id = t.id " +
                            "WHERE t.name IN (" + tagNames + "))");
                }
                if (StringUtils.isNotBlank(request.getSortField()) && StringUtils.isNotBlank(request.getSortOrder())) {
                    ORDER_BY(request.getSortField() + " " + request.getSortOrder());
                }
                GROUP_BY("q.id");  // Add GROUP BY to avoid duplicate rows

            }
        };
        return sql.toString();
    }
}
