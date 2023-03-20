package com.test.subjectthings.service.Impl;

import com.test.subjectthings.Util.FileMaker;
import com.test.subjectthings.dto.BoardDto;
import com.test.subjectthings.service.BoardService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Override
    public void addFiles() throws IOException {
        FileMaker fm = new FileMaker();
        fm.fileMaker();
    }

    @Override
    public List<BoardDto> getList() throws IOException {

        FileMaker fm = new FileMaker();
        return fm.getLists();
    }

    @Override
    public void addBoard(BoardDto dto) throws IOException {

        FileMaker fm = new FileMaker();
        String line = fm.lineMaker(dto,isCheck(dto.isStrong()));
        fm.addBoards(line);


    }

    @Override
    public void modifyBoard(BoardDto dto) {

        FileMaker fm = new FileMaker();
        fm.modify(dto);
    }

    @Override
    public void removeBoard(BoardDto dto) throws IOException {
        FileMaker fm = new FileMaker();
        fm.fileRemover(dto);
    }

    @Override
    public BoardDto getDetailBoard(int no) {

        FileMaker fm = new FileMaker();
        return fm.getDetails(no);
    }

    @Override
    public List<BoardDto> getSearchList(String type, String keyword) {

        FileMaker fm = new FileMaker();
        return fm.searchList(type,keyword);
    }

    private String isCheck(boolean check) {

        return check ? "true" : "false";
    }

    @Override
    public int findAllCnt() {

        FileMaker fm = new FileMaker();
        return fm.getCount();
    }
}
