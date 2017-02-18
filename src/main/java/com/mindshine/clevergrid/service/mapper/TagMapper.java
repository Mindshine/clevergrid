package com.mindshine.clevergrid.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.mindshine.clevergrid.domain.Tag;
import com.mindshine.clevergrid.service.dto.TagDTO;

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
