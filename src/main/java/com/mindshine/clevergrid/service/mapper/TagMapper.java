package com.mindshine.clevergrid.service.mapper;

import com.mindshine.clevergrid.domain.*;
import com.mindshine.clevergrid.service.dto.TagDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Tag and its DTO TagDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TagMapper {

    TagDTO tagToTagDTO(Tag tag);

    List<TagDTO> tagsToTagDTOs(List<Tag> tags);

    Tag tagDTOToTag(TagDTO tagDTO);

    List<Tag> tagDTOsToTags(List<TagDTO> tagDTOs);
}
