package com.test.subjectthings.controller;

import com.test.subjectthings.Util.FileMaker;
import com.test.subjectthings.dto.BoardDto;
import com.test.subjectthings.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {


    private final BoardService boardService;

    @GetMapping("/createBoard")
    public String addBoards() throws IOException {

        return "/board/register";
    }

    @GetMapping("/detail/{no}")
    public String detailBoard(@PathVariable String no, Model model) throws IOException {

        int nos = Integer.parseInt(no);
        BoardDto board = boardService.getDetailBoard(nos);
        model.addAttribute("board", board);

        return "/board/detail";
    }
    @GetMapping("/search")
    public String getCheckList(Model model,@RequestParam(value="searchType",required = false)String searchType,
                                          @RequestParam(value="keyword",required = false)String keyword) throws IOException {



        List<BoardDto> list = boardService.getSearchList(searchType, keyword);
        model.addAttribute("list",list);

        return "/board/getList";
    }

    @GetMapping
    public String getLists(Model model) throws IOException {



        List<BoardDto> list = boardService.getList();
        model.addAttribute("list", list);


        return "/board/getList";
    }




    @PostMapping
    public String addBoard(BoardDto dto) throws IOException {


        boardService.addBoard(dto);


        return "redirect:/board";
    }

    @PostMapping("/nickName")
    public String addNickName(BoardDto dto,HttpSession session, Model model) throws IOException {

        session.setAttribute("nickName", dto.getNickName());
        session.setMaxInactiveInterval(10 * 60);

        FileMaker fm = new FileMaker();
        if(!fm.fileChecker()){
            fm.fileMaker();
        }


        List<BoardDto> list = boardService.getList();
        model.addAttribute("list", list);


        return "/board/getList";

    }

    @PostMapping("/modify")
    public String modifyThings(BoardDto dto) {
        boardService.modifyBoard(dto);
        return "redirect:/board";
    }

    @PostMapping("/delete")
    public String deleteBoard(BoardDto dto) throws IOException {

        boardService.removeBoard(dto);

        return "redirect:/board";
    }


}
