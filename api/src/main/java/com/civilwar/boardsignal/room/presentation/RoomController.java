package com.civilwar.boardsignal.room.presentation;

import com.civilwar.boardsignal.room.application.RoomService;
import com.civilwar.boardsignal.room.dto.mapper.RoomApiMapper;
import com.civilwar.boardsignal.room.dto.request.ApiCreateRoomRequest;
import com.civilwar.boardsignal.room.dto.request.CreateRoomResponse;
import com.civilwar.boardsignal.room.dto.response.CreateRoomRequest;
import com.civilwar.boardsignal.user.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Room API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "방 생성 API")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping
    public ResponseEntity<CreateRoomResponse> createRoom(
        @AuthenticationPrincipal User user,
        @RequestPart(value = "image", required = false) MultipartFile image,
        @Valid @RequestPart(value = "data") ApiCreateRoomRequest request
    ) {
        CreateRoomRequest createRoomRequest = RoomApiMapper.toCreateRoomRequest(image, request);

        CreateRoomResponse createRoomResponse = roomService.createRoom(user, createRoomRequest);

        return ResponseEntity.ok(createRoomResponse);
    }
}
