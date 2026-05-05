package com.API.api.application.command;

import java.util.List;

import com.API.api.domain.model.Board;
import com.API.api.domain.model.Column;
import com.API.api.domain.model.Task;

public record BoardDetailCommand(
        Board board,
        List<Column> columns,
        List<Task> tasks) {

}
