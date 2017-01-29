package com.mindshine.clevergrid.service.mapper;

import com.mindshine.clevergrid.domain.*;
import com.mindshine.clevergrid.service.dto.TopicDTO;

import org.mapstruct.*;
import java.util.List;

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
