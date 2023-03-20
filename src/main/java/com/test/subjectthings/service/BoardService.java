package com.test.subjectthings.service;


import com.test.subjectthings.dto.BoardDto;
import com.test.subjectthings.dto.SearchDto;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    public List<BoardDto> getList() throws IOException;

    public void addFiles() throws IOException;

    public void addBoard(BoardDto dto) throws IOException;

    public void modifyBoard(BoardDto dto);

    public void removeBoard(BoardDto dto) throws IOException;

    BoardDto getDetailBoard(int no);

    List<BoardDto> getSearchList(String type, String keyword);

    int findAllCnt();
}
