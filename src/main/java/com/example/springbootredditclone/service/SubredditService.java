package com.example.springbootredditclone.service;

import com.example.springbootredditclone.dto.SubredditDto;
import com.example.springbootredditclone.exceptions.SpringRedditException;
import com.example.springbootredditclone.exceptions.SubredditNotFoundException;
import com.example.springbootredditclone.mapper.SubredditMapper;
import com.example.springbootredditclone.model.Subreddit;
import com.example.springbootredditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditService {



    private static  Logger logger = LoggerFactory.getLogger(SubredditService.class);
    private final DtoToEntityConverter mapdto2Entity;
    private final SubredditRepository subredditRepository;
    private final ModelMapper modelMapper;
    private final SubredditMapper subredditMapper;

 @Transactional
 public SubredditDto save(SubredditDto subredditDto){
//      Subreddit subreddit = subredditRepository.save(mapdto2Entity.convertSubreddit2SubredditDto(subredditDto));
     Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
     subredditDto.setId(subreddit.getId());
     return subredditDto;
 }

    @Transactional(readOnly= true)
    public List<SubredditDto> getAll() {
   return subredditRepository.findAll().stream()
//             .map(this::entity2Dto)
           .map(subredditMapper::mapSubredditToDto)
           .collect(Collectors.toList());
    }

    public SubredditDto getSubreddit(Long id) {
    return subredditMapper.mapSubredditToDto(subredditRepository.findById(id).orElseThrow(()
    -> new SpringRedditException("subreddit not found for this Id"+id))) ;
 }

    private SubredditDto entity2Dto(Subreddit subreddit) {
        SubredditDto subredditDto = new SubredditDto();
        return SubredditDto.builder().id(subreddit.getId())
                .name(subreddit.getName())
                .numberOfPosts(subreddit.getPosts().size())
                .description(subreddit.getDescription()).build()
                ;
        //with model mapper
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
//        return modelMapper.map(subreddit,SubredditDto.class);
    }
//

    @Transactional
    public SubredditDto updateSubreddit(SubredditDto subredditDto,Long id){
     Subreddit subreddit = subredditRepository.findById(id).
             orElseThrow(()-> new SubredditNotFoundException(id.toString()));
     subreddit.setDescription(subredditDto.getDescription());
     Subreddit updatedSubreddit = subredditRepository.save(subreddit);
     return subredditMapper.mapSubredditToDto(updatedSubreddit);

    }
}
