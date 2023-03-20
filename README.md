# A1performance
AI 제출용

# 구현 요구사항

1. 게시판은 리스트, 조회(읽기), 쓰기(수정) 화면으로 구성 됩니다. ✅
2. 입력은 타이틀, 본문, 닉네임 입니다. ✅
3. 한번 작성한 닉네임은 다음번 게시물 작성시 자동으로 입력 되도록 합니다. ✅
4. 게시물에 대한 삭제가 가능하여 합니다. ✅
5. 게시물에는 작성시 중요 게시물 체크가 가능합니다. ✅
6. 리스트에서 중요 게시물의 경우에는 배경이 다른 색으로 표현이 되어야 합니다. ✅
7. 타이틀과 닉네임에 대해 검색 기능을 추가 바랍니다. ✅
8. 데이터베이스를 사용하지 않고 구현 바랍니다. ✅
9. 페이징 기능을 추가 바랍니다.(옵션 사항)

# 도메인 flow 

1. 인덱스 페이지에서 닉네임 입력후 버튼 클릭시 post 방식으로 닉네임이 session에 저장됨. 
2. 저장된 닉네임을 inputbox 에 확인 가능하게 노출
3. 등록 기능: a 태그를 타고 form 태그안에 양식을 입력하면 해당 데이터가 저장됨.(중요도는 checkbox로 선택가능) 
4. 수정 기능: 수정하고싶은 board를 하나 클릭후 선택수정 버튼을 클릭한다면 해당 board의 no가 넘어가서 no로 조회한 결과를 화면에 반환한다. 
              => title, content, 중요도 수정가능  no는 readonly처리 
5. 조회 기능: no를 클릭시 해당 board 상세내역을 확인가능 readonly처리 
6. 전체 리스트 조회시 중요도가 checked 된 row는 노란색으로 처리 
7. selectbox를 통해 title, nickName을 선택하여 keyword를 get 방식으로 전송.
8. db 없이 file에 string 타입으로 data가 한줄로 저장 "/"로 row를 구분, "-" 로 데이터의 column을 구분지어 기능 구현
9. 페이징처리 실패               