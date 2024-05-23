package com.github.kotooriiii.myworld.util.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.kotooriiii.myworld.model.Author;
import com.github.kotooriiii.myworld.model.ChangeLog;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuditLogUtils
{

    /**
     * Substitutes the JSON path placeholders in the resource bundle message with the given jsonNode attributes.
     *
     * @param message  The Audit Log event message
     * @param jsonNode The root node of the source and target
     * @return the complete event detail message after placeholders have been substituted. If the message cannot be substituted, will //todo
     */
    public static String substitute(String message, JsonNode jsonNode)
    {
        Pattern pattern = Pattern.compile("\\{([^}]+)\\}");
        Matcher matcher = pattern.matcher(message);

        StringBuffer result = new StringBuffer();
        while (matcher.find())
        {
            String jsonPath = matcher.group(1);
            String value = getValueFromJsonPath(jsonPath, jsonNode);


            if (StringUtils.isBlank(value))
            {
                //todo
                //The path does not exist
                //print entity, segment that does not exist, and bundle
            }

            matcher.appendReplacement(result, value);
        }
        matcher.appendTail(result);
        return result.toString();
    }

    /**
     * Gets the value of a JsonNode at a specific path.
     *
     * @param jsonPath
     * @param jsonNode
     * @return
     */
    private static String getValueFromJsonPath(String jsonPath, JsonNode jsonNode)
    {
        String[] parts = jsonPath.split("\\.");
        JsonNode currentNode = jsonNode;
        for (String part : parts)
        {
            currentNode = currentNode.get(part);
            if (currentNode == null || currentNode.isMissingNode())
            {
                return ""; // or throw an exception if desired
            }
        }
        return currentNode.asText();
    }

    /**
     * Creates a new JsonNode appending the JSON representations of source and target.
     *
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <S,T> JsonNode getRootJsonNode(S source, T target, List<ChangeLog> changeLogList)
    {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode sourceJsonNode = mapper.valueToTree(source);
        JsonNode targetJsonNode = mapper.valueToTree(target);

        ObjectNode rootNode = mapper.createObjectNode();

        rootNode.set("source", sourceJsonNode);
        rootNode.set("target", targetJsonNode);
        rootNode.set("changeLogListSize", mapper.valueToTree(changeLogList.size()));


        return rootNode;
    }

    /**
     * Creates a new JsonNode appending the JSON representations of oldValue and newValue.
     *
     * @param oldValue
     * @param newValue
     * @return
     */
    public static JsonNode getRootJsonNode(String oldValue, String newValue)
    {
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            JsonNode sourceJsonNode = mapper.readTree(oldValue);
            JsonNode targetJsonNode = mapper.readTree(newValue);

            ObjectNode rootNode = mapper.createObjectNode();

            rootNode.set("old", sourceJsonNode);
            rootNode.set("new", targetJsonNode);


            return rootNode;
        } catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }
}
