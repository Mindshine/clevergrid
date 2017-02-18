package com.mindshine.clevergrid.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.mindshine.clevergrid.domain.Topic;
import com.mindshine.clevergrid.service.dto.TopicDTO;

/**
 * Mapper for the entity Topic and its DTO TopicDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TopicMapper {

    TopicDTO topicToTopicDTO(Topic topic);

    List<TopicDTO> topicsToTopicDTOs(List<Topic> topics);

    Topic topicDTOToTopic(TopicDTO topicDTO);

    List<Topic> topicDTOsToTopics(List<TopicDTO> topicDTOs);
}
