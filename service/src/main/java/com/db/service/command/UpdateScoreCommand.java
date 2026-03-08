package com.db.service.command;

import com.db.service.dto.request.ScoreRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateScoreCommand {
    private Long id;
    private ScoreRequest request;
}
