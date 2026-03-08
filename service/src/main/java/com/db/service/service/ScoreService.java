package com.db.service.service;

import com.db.service.command.CreateScoreCommand;
import com.db.service.command.DeleteScoreCommand;
import com.db.service.command.UpdateScoreCommand;
import com.db.service.dto.response.ScoreResponse;

import java.util.List;

public interface ScoreService {
    ScoreResponse execute(CreateScoreCommand command);
    ScoreResponse execute(UpdateScoreCommand command);
    void execute(DeleteScoreCommand command);
    List<ScoreResponse> findAll();
    ScoreResponse findById(Long id);
}
