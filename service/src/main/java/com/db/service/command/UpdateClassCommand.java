package com.db.service.command;

import com.db.service.dto.request.ClassRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClassCommand {
    private Long id;
    private ClassRequest request;
}
