package com.db.service.service.impl;

import com.db.service.command.CreateRoomCommand;
import com.db.service.command.DeleteRoomCommand;
import com.db.service.command.UpdateRoomCommand;
import com.db.service.dto.response.RoomResponse;
import com.db.service.entity.Room;
import com.db.service.repository.RoomRepository;
import com.db.service.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;

    @Override
    @Transactional
    public RoomResponse execute(CreateRoomCommand command) {
        var req = command.getRequest();
        var entity = Room.builder()
                .roomCode(req.getRoomCode())
                .building(req.getBuilding())
                .capacity(req.getCapacity())
                .roomType(req.getRoomType())
                .description(req.getDescription())
                .build();
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public RoomResponse execute(UpdateRoomCommand command) {
        var entity = repository.findById(command.getId()).orElseThrow(() -> new RuntimeException("Room not found: " + command.getId()));
        var req = command.getRequest();
        entity.setRoomCode(req.getRoomCode());
        entity.setBuilding(req.getBuilding());
        entity.setCapacity(req.getCapacity());
        entity.setRoomType(req.getRoomType());
        entity.setDescription(req.getDescription());
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public void execute(DeleteRoomCommand command) {
        repository.deleteById(command.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RoomResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Room not found: " + id));
        return toResponse(entity);
    }

    private RoomResponse toResponse(Room e) {
        return RoomResponse.builder()
                .roomId(e.getRoomId())
                .roomCode(e.getRoomCode())
                .building(e.getBuilding())
                .capacity(e.getCapacity())
                .roomType(e.getRoomType())
                .description(e.getDescription())
                .build();
    }
}
