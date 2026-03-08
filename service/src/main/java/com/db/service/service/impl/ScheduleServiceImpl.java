package com.db.service.service.impl;

import com.db.service.command.CreateScheduleCommand;
import com.db.service.command.DeleteScheduleCommand;
import com.db.service.command.UpdateScheduleCommand;
import com.db.service.dto.response.ScheduleResponse;
import com.db.service.entity.CourseClass;
import com.db.service.entity.Room;
import com.db.service.entity.Schedule;
import com.db.service.repository.CourseClassRepository;
import com.db.service.repository.RoomRepository;
import com.db.service.repository.ScheduleRepository;
import com.db.service.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository repository;
    private final CourseClassRepository courseClassRepository;
    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public ScheduleResponse execute(CreateScheduleCommand command) {
        var req = command.getRequest();
        CourseClass courseClass = courseClassRepository.findById(req.getCourseClassId()).orElseThrow(() -> new RuntimeException("CourseClass not found"));
        Room room = roomRepository.findById(req.getRoomId()).orElseThrow(() -> new RuntimeException("Room not found"));
        var entity = Schedule.builder()
                .courseClass(courseClass)
                .room(room)
                .dayOfWeek(req.getDayOfWeek())
                .periodStart(req.getPeriodStart())
                .periodEnd(req.getPeriodEnd() != null ? req.getPeriodEnd() : req.getPeriodStart())
                .note(req.getNote())
                .build();
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public ScheduleResponse execute(UpdateScheduleCommand command) {
        var entity = repository.findById(command.getId()).orElseThrow(() -> new RuntimeException("Schedule not found: " + command.getId()));
        var req = command.getRequest();
        CourseClass courseClass = courseClassRepository.findById(req.getCourseClassId()).orElseThrow(() -> new RuntimeException("CourseClass not found"));
        Room room = roomRepository.findById(req.getRoomId()).orElseThrow(() -> new RuntimeException("Room not found"));
        entity.setCourseClass(courseClass);
        entity.setRoom(room);
        entity.setDayOfWeek(req.getDayOfWeek());
        entity.setPeriodStart(req.getPeriodStart());
        entity.setPeriodEnd(req.getPeriodEnd() != null ? req.getPeriodEnd() : req.getPeriodStart());
        entity.setNote(req.getNote());
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public void execute(DeleteScheduleCommand command) {
        repository.deleteById(command.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Schedule not found: " + id));
        return toResponse(entity);
    }

    private ScheduleResponse toResponse(Schedule e) {
        String label = e.getCourseClass().getSubject().getSubjectName() + " - " + e.getCourseClass().getSemester().getSemesterName();
        return ScheduleResponse.builder()
                .scheduleId(e.getScheduleId())
                .courseClassId(e.getCourseClass().getCourseClassId())
                .courseClassLabel(label)
                .roomId(e.getRoom().getRoomId())
                .roomCode(e.getRoom().getRoomCode())
                .dayOfWeek(e.getDayOfWeek())
                .periodStart(e.getPeriodStart())
                .periodEnd(e.getPeriodEnd())
                .note(e.getNote())
                .build();
    }
}
