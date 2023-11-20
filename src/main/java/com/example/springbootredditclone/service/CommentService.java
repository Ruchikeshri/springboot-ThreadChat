package com.example.springbootredditclone.service;

import com.example.springbootredditclone.dto.CommentsDto;
import com.example.springbootredditclone.exceptions.PostNotFoundException;
import com.example.springbootredditclone.exceptions.SpringRedditException;
import com.example.springbootredditclone.mapper.CommentsMapper;
import com.example.springbootredditclone.model.Comment;
import com.example.springbootredditclone.model.NotificationEmail;
import com.example.springbootredditclone.model.Post;
import com.example.springbootredditclone.model.User;
import com.example.springbootredditclone.repository.CommentRepository;
import com.example.springbootredditclone.repository.PostRepository;
import com.example.springbootredditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentService {
    private static final String POST_URL="";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentsMapper commentsMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto){
     Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(()-> new PostNotFoundException(commentsDto.getPostId().toString()));
         Comment comment = commentsMapper.map(commentsDto,post,authService.getCurrentUser());
         commentRepository.save(comment);
         String message = mailContentBuilder.build(post.getUser().getUserName()+ " just posted a comment on  your post."+POST_URL );
         sendNotification(message,post.getUser());
    }

    private void sendNotification(String message, User user) {
    mailService.sendMail(new NotificationEmail(user.getUserName()+"posted Comment on your Post",user.getEmail(),message));
    }

    public List<CommentsDto> getAllComments(Long postId){
//     Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException(postId.toString()));
      Optional<Post> post = postRepository.findById(postId);
      if(post.isPresent()) {
          return commentRepository.findByPost(post.get()).stream()
                  .map(commentsMapper::mapToDto).collect(Collectors.toList());
      }
      else {
          throw new PostNotFoundException();
      }
    }


    public List<CommentsDto> getALLCommentsForUser(String userName) {
     Optional<User> user= userRepository.findByUserName(userName);
     if( user.isPresent() ){
         return commentRepository.findAllByUser(user)
                 .stream()
                 .map(commentsMapper::mapToDto)
                 .collect(Collectors.toList());
     }
     else {
         throw new UsernameNotFoundException("not found");
     }
    }

    public boolean containSwearWords(String comment){
        if(comment.contains("shit")){
            throw new SpringRedditException(
                    "comment contain unacceptable language");
        }
        return false;
    }
}
