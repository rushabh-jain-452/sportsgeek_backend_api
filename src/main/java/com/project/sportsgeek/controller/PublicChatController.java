package com.project.sportsgeek.controller;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.PublicChat;
import com.project.sportsgeek.model.PublicChatFormatted;
import com.project.sportsgeek.model.PublicChatWithUser;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.PublicChatService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping(path = "/public-chat",produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicChatController {

    @Autowired
    PublicChatService publicChatService;

    // Dangerous when Table has large number of records
//    @ApiResponses(value =
//            {
//                    @ApiResponse(code = 200, message = "success", response = PublicChat.class),
//                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
//            }
//    )
//    @PreAuthorize("hasAnyRole('Admin','User')")
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<PublicChatWithUser>> getAllPublicChat() {
//        Result<List<PublicChatWithUser>> publicChatList = publicChatService.findAllPublicChat();
//        return new ResponseEntity<>(publicChatList.getData(), HttpStatus.valueOf(publicChatList.getCode()));
//    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = PublicChat.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/last-days/{days}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PublicChatWithUser>> getAllPublicChatForLastDays(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int days) {
        if(days > 30){
            days = 30;  // Set Max days
        }
        Result<List<PublicChatWithUser>> publicChatList = publicChatService.findAllPublicChatForLastDays(days);
        return new ResponseEntity<>(publicChatList.getData(), HttpStatus.valueOf(publicChatList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = PublicChat.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/after-id/{publicChatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PublicChatWithUser>> getAllPublicChatAfterId(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int publicChatId) {
        Result<List<PublicChatWithUser>> publicChatList = publicChatService.findAllPublicChatAfterId(publicChatId);
        return new ResponseEntity<>(publicChatList.getData(), HttpStatus.valueOf(publicChatList.getCode()));
    }

    // Formatted PublicChat
//    @ApiResponses(value =
//            {
//                    @ApiResponse(code = 200, message = "success", response = PublicChat.class),
//                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
//            }
//    )
//    @PreAuthorize("hasAnyRole('Admin','User')")
//    @GetMapping(value = "/formatted", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<PublicChatFormatted>> getAllPublicChatFormatted() {
//        Result<List<PublicChatFormatted>> publicChatList = publicChatService.findAllPublicChatFormatted();
//        return new ResponseEntity<>(publicChatList.getData(), HttpStatus.valueOf(publicChatList.getCode()));
//    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = PublicChat.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/formatted/last-days/{days}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PublicChatFormatted>> getAllPublicChatFormattedForLastDays(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int days) {
        if(days > 30){
            days = 30;  // Set Max days
        }
        Result<List<PublicChatFormatted>> publicChatList = publicChatService.findAllPublicChatFormattedForLastDays(days);
        return new ResponseEntity<>(publicChatList.getData(), HttpStatus.valueOf(publicChatList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = PublicChat.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/formatted/after-id/{publicChatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PublicChatFormatted>> getAllPublicChatFormattedAfterId(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int publicChatId) {
        Result<List<PublicChatFormatted>> publicChatList = publicChatService.findAllPublicChatFormattedAfterId(publicChatId);
        return new ResponseEntity<>(publicChatList.getData(), HttpStatus.valueOf(publicChatList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = PublicChat.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/{publicChatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PublicChatWithUser> getPublicChatById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int publicChatId) throws Exception {
        Result<PublicChatWithUser> publicChatList = publicChatService.findPublicChatById(publicChatId);
        return new ResponseEntity<>(publicChatList.getData(), HttpStatus.valueOf(publicChatList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = PublicChat.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PublicChat> addPublicChat(@RequestBody(required = true) @Valid PublicChat publicChat) throws Exception {
        Result<PublicChat> publicChatResult = publicChatService.addPublicChat(publicChat);
        return new ResponseEntity(publicChatResult.getData(), HttpStatus.valueOf(publicChatResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = PublicChat.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @PutMapping(value = "/{publicChatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PublicChat> updatePublicChat(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int publicChatId, @RequestBody(required = true) @Valid PublicChat publicChat) throws Exception {
        Result<PublicChat> publicChatResult = publicChatService.updatePublicChat(publicChatId, publicChat);
        return new ResponseEntity(publicChatResult.getData(), HttpStatus.valueOf(publicChatResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = PublicChat.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin')")
    @PutMapping(value = "/{publicChatId}/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result<String>> updatePublicChatStatus(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int publicChatId, @PathVariable @Valid boolean status) throws Exception {
        Result<String> result = publicChatService.updatePublicChatStatus(publicChatId, status);
        return new ResponseEntity(result, HttpStatus.valueOf(result.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = PublicChat.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin')")
    @DeleteMapping(value = "/{publicChatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result<String>> deletePublicChatById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int publicChatId) throws Exception {
        Result<String> result =  publicChatService.deletePublicChat(publicChatId);
        return new ResponseEntity(result, HttpStatus.valueOf(result.getCode()));
    }
}
