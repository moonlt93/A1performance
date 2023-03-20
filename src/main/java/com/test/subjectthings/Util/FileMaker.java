package com.test.subjectthings.Util;

import com.test.subjectthings.dto.BoardDto;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class FileMaker {


   /* 파일 생성 */
    public void fileMaker() throws IOException {

        String path = "ListLine.txt";

        try {
            File f = new File(path);
            if (f.createNewFile()) {
                log.info("file 생성");
            } else {
                log.info("같은 이름의 file이 존재합니다.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* board 저장 시 line creator */
    public String lineMaker(BoardDto dto, String strong) {

        return dto.getTitle() + '-' + dto.getContent() + '-' + dto.getNickName() + '-' + strong;
    }


    /* 해당 line details return */
    public BoardDto getDetails(int no) {
        OutputStream os = null;
        String path = "ListLine.txt";

        BoardDto reDto = null;
        try {

            StringBuilder lines = fileReader(path);

            String[] arr = lines.toString().split("/");


            for (int i = 0; i < arr.length; i++) {
                if (no == i) {
                    String[] lists = arr[i].split("-");
                    reDto = new BoardDto();
                    reDto.setNo(i);
                    reDto.setTitle(lists[0]);
                    reDto.setContent(lists[1]);
                    reDto.setNickName(lists[2]);
                    reDto.setStrong(Boolean.parseBoolean(lists[3]));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reDto;
    }

    /* 전체 list return */
    public List<BoardDto> getLists() throws IOException {
        OutputStream os = null;
        String path = "ListLine.txt";

        ArrayList<BoardDto> list = new ArrayList<>();

        try {

            StringBuilder lines = fileReader(path);

            String[] arr = lines.toString().split("/");

            if (!arr[0].isEmpty() || arr.length > 1) {
                for (int i = 0; i < arr.length; i++) {

                        String[] lists = arr[i].split("-");
                        listMaker(list, i, lists);
                }
            }
            return list;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }



    /* 추가 */
    public void addBoards(String line) {

        OutputStream os = null;
        String path = "ListLine.txt";

        try {

            File file = new File(path);

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            StringBuilder lines = new StringBuilder();

            String maker;

            while ((maker = br.readLine()) != null) {
                lines.append(maker);
            }

            lines.append(line).append("/");

            byte[] buffers = lines.toString().getBytes();
            os = new FileOutputStream(path);

            for (byte b : buffers
            ) {
                os.write(b);
            }
            os.flush();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /* 수정 */
    public void modify(BoardDto dto) {

        OutputStream os = null;
        String path = "ListLine.txt";

        try {

            StringBuilder lines = fileReader(path);
            StringBuilder result = new StringBuilder();

            String[] arr = lines.toString().split("/");

            for (int i = 0; i < arr.length; i++) {
                String[] lists = arr[i].split("-");

                if (dto.getNo() == i) {
                    arr[i] = modifyMaker(lists, dto);
                }

                result.append(arr[i]).append("/");
            }


            if ("/".equals(result.substring(result.length() - 2, result.length() - 1))) {
                result = new StringBuilder(result.substring(0, result.length() - 2));
            }


            byte[] buffers = result.toString().getBytes();
            os = new FileOutputStream(path);

            for (byte b : buffers
            ) {
                os.write(b);
            }
            os.flush();
            os.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /*  검색한 list  return */
    public List<BoardDto> searchList(String type, String keyword) {

        OutputStream os = null;
        String path = "ListLine.txt";

        HashMap<String, String> map = createMap(type, keyword);
        ArrayList<BoardDto> list = new ArrayList<>();

        try {

            StringBuilder lines = fileReader(path);

            String[] arr = lines.toString().split("/");

            if (!arr[0].isEmpty()) {
                for (int i = 0; i < arr.length; i++) {
                    String[] lists = arr[i].split("-");
                    if (map.get(type).equals(lists[0]) || map.get(type).equals(lists[2])) {
                        listMaker(list, i, lists);
                    }
                }
            }

            return list;

        } catch (IOException e) {

          e.printStackTrace();

        }
        return list;
    }


    /* 파일 존재 여부 확인 */
    public boolean fileChecker() {
        String path = "ListLine.txt";

        File f = new File(path);

        return f.exists();
    }

    /* 해당 line 제거 */
    public void fileRemover(BoardDto dto) throws IOException {
        OutputStream os = null;
        String path = "ListLine.txt";
        try {
            StringBuilder lines = fileReader(path);

            StringBuilder result = new StringBuilder();

            String[] arr = lines.toString().split("/");
            String[] ids = dto.getIdList().split(",");

                for (String numbers:ids
                     ) {
                    int no = 0;
                    try{
                        no = Integer.parseInt(numbers);
                        for (int i = 0; i < arr.length; i++) {
                            if (no == i) {
                                arr[i] = "";
                                result.append("/");
                            }
                            result.append(arr[i]).append("/");
                        }
                    }catch (Exception e) {
                        log.error("remove fail");
                    }
                }

            result = resultChecker(result);

            if(result != null) {
                byte[] buffers = result.toString().getBytes();
                os = new FileOutputStream(path);

                for (byte b : buffers
                ) {
                    os.write(b);
                }
                os.flush();
                os.close();
            }else{
                os = new FileOutputStream(path);
                os.flush();
                os.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private StringBuilder resultChecker(StringBuilder result) {

       String line= result.toString();
        Queue<String> q=  new LinkedList<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
          q.add(String.valueOf(line.charAt(i)));
        }
        int count =0;
        while(!q.isEmpty()){
           String value= q.poll();
           if("/".equals(value)){
               if(count == 0){
                   sb.append(value);
               }
               count++;
               continue;
           }
           sb.append(value);
        }
        if("/".equals(sb.toString())){
            return null;
        }

        return sb;
    }

    private String lineChecker(StringBuilder lines) {
        if(lines.length()>1) {
            String start = lines.substring(0, 1);

            if ("/".equals(start)) {
                return lines.substring(1, lines.length());
            }
        }
        return lines.toString();
    }

    /* list 총 갯수 */
    public int getCount() {
        OutputStream os = null;
        String path = "ListLine.txt";
        int totalCount = 0;

        try {
            StringBuilder lines = fileReader(path);
            String[] arr = lines.toString().split("/");
            totalCount = arr.length + 1;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return totalCount;
    }

    /* fileReader */
    private StringBuilder fileReader(String path) throws IOException {

        File file = new File(path);

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder lines = new StringBuilder();

        String maker;
        while ((maker = br.readLine()) != null) {
            lines.append(maker);
        }

        lines= new StringBuilder(lineChecker(lines));

        return lines;
    }

    // 조회 타입에 따른 value
    private HashMap<String, String> createMap(String type, String keyword) {
        HashMap<String, String> map = new HashMap<>();
        map.put(type, keyword);
        return map;
    }


    /* Board list 추가 */
    private void listMaker(ArrayList<BoardDto> list, int i, String[] lists) {
        BoardDto reDto = new BoardDto();

        reDto.setNo(i);
        reDto.setTitle(lists[0]);
        reDto.setContent(lists[1]);
        reDto.setNickName(lists[2]);
        reDto.setStrong(Boolean.parseBoolean(lists[3]));


        list.add(reDto);
    }

    /* 수정된 row값 변환 */
    private String modifyMaker(String[] lists, BoardDto dto) {


        if (!dto.getTitle().isEmpty()) {
            lists[0] = dto.getTitle();
        }
        if (!dto.getContent().isEmpty()) {
            lists[1] = dto.getContent();
        }
        if(Boolean.parseBoolean(lists[3]) != dto.isStrong()){
            lists[3] = String.valueOf(dto.isStrong());
        }


        return lists[0] + '-' + lists[1] + '-' + dto.getNickName() + '-' + lists[3];
    }

}




