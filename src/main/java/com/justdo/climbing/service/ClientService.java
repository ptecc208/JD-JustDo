package com.justdo.climbing.service;

import com.justdo.climbing.dto.member.ClientDTO;
import com.justdo.climbing.repository.ClimbingRepository;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ClientService {
    static Scanner sc = new Scanner(System.in);

    private ClimbingRepository repository = ClimbingRepository.getInstance();
    private ClientDTO identifiedMember = null;

    public ClientDTO ClientLogin(String phoneNum,String userName){

//        ClientDTO identifiedMember = null;

//        while (identifiedMember == null) {
            try {
                sc = new Scanner(System.in);
//                System.out.print("핸드폰번호를 입력해주세요(010, ' - ' 제외 8자리 : ");
//                String phoneNum = sc.nextLine();
//                if (phoneNum.length() != 8) {
//                    throw new IllegalArgumentException("허용되지 않은 번호입니다. 010, ' - ' 제외 숫자 8자리 입력해주세요");
//                }
                System.out.println(""" 
                1. 천안
                2. 시흥
                3. 강남점
                4. 양재점
                5. 역삼점
                6. 선릉점
                7. 삼성점""");
                System.out.print("가입하신 지점을 목록에서 선택해주세요. : ");
                int center = sc.nextInt();
                sc.nextLine();
                if (center < 0) {
                    throw new IllegalArgumentException("지점 번호는 음수일 수 없습니다.");
                }

                // 입력받은 핸드폰 번호와 기존 번호를 기존의 list와 비교
                if (repository.getClientListInfoList() != null && !repository.getClientListInfoList().isEmpty()) {
                    for (ClientDTO c : repository.getClientListInfoList()) {
                        if (c.getMemberPhone().equals(phoneNum) && userName.equals(c.getMemberName()) && c.getCenter() == center) {
                            identifiedMember = c;
                            break;
                        }
                    }
                }
                if (identifiedMember == null) {
                    System.out.println("가입되어있지 않는 회원이 아닙니다. 다시 입력해주세요");
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력 형식입니다. 다시 입력해주세요.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        return identifiedMember;
    }

    public void AddClientInfo(){

        List<ClientDTO> clientDTOList = repository.getClientListInfoList();
        while (true) {
            System.out.print("""
                    ========================================
                    회원 가입 메뉴입니다.
                    핸드폰번호를 입력해주세요
                    (010, ' - ' 제외 8자리)
                    뒤로 가기를 원하시면 ' 9 '를 눌러주세요
                    ========================================
                    =====숫자를 입력해주세요 :""");
            String phoneNum = sc.nextLine();

            try {
                if (Integer.parseInt(phoneNum) == 9) {
                    return;
                } else if (phoneNum.length() != 8) {
                    throw new IllegalArgumentException("허용되지 않은 번호입니다. 010, ' - ' 제외 숫자 8자리 입력해주세요");
                }
            } catch (NumberFormatException e) {
                System.out.println("올바른 숫자 형식이 아닙니다. 다시 입력해주세요");
                continue;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
                continue;
            }

            System.out.println("이름을 입력해주세요");
            String name = sc.nextLine();

            try {
                boolean isAdded = false;
                if (clientDTOList != null && !clientDTOList.isEmpty()) {

                    for (ClientDTO c : clientDTOList) {
                        if (c.getMemberPhone().equals(phoneNum)) {
                            System.out.println("이미 등록되어있는 핸드폰 번호입니다.");
                            isAdded = true;
                            break;
                        }
                    }

                }
                if (!isAdded) {

                    System.out.println(""" 
                        1. 천안
                        2. 시흥
                        3. 강남점
                        4. 양재점
                        5. 역삼점
                        6. 선릉점
                        7. 삼성점""");
                    System.out.print("이용하실 지점을 목록에서 선택해주세요. : ");
                    int center = sc.nextInt();
                    sc.nextLine();
                    System.out.print("성별을 입력하세요 남(1), 여(2) : ");
                    int gender = sc.nextInt();
                    sc.nextLine();
                    System.out.println("나이를 입력해주세요");
                    int age = sc.nextInt();

                    ClientDTO newMember = new ClientDTO(name, phoneNum, center, gender==1, age, 0, 0);
                    clientDTOList.add(newMember);
                    System.out.println("회원 등록이 완료되었습니다.");
                    sc.nextLine();
                    return;
                }
            } catch (InputMismatchException e) {
                System.out.println("올바른 입력 형식이 아닙니다. 다시 입력해주세요");
                sc.nextLine();
            }
        }
    }

    public void purchase(String phoneNumber){
        identifiedMember = repository.getLoginClientInfo(phoneNumber);
        if (identifiedMember==null){
            System.out.println("회원정보가 없습니다. 다시확인해주세요.");
            return;
        }
        while (true) {
            System.out.println("""
                    ========================================
                    이용권 구매창입니다.
                    1  /  3  /  6 개월 중 하나를 선택하세요.
                    구매를 원하지 않으시면 ' 9 '를 눌러주세요
                    ========================================
                        숫자를 입력해주세요 :""");
            int command;
            try {
                command = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("올바른 숫자 형식이 아닙니다. 다시 입력해주세요");
                continue;
            }


            if(identifiedMember.getDuration() == 0){
                // TODO:  물품 -1
            }


            // TODO:이용권 + 개월수
            switch (command) {
                case 1:
                    System.out.println("1개월 이용권을 선택하셨습니다.");
                    //물품 재고 -1
                    identifiedMember.setDuration(identifiedMember.getDuration() + 1);
                    //내 정보 출력
                    System.out.println("=====================================");
                    System.out.println("회원 이름: " + identifiedMember.getMemberName());
                    System.out.println("회원 전화번호: " + identifiedMember.getMemberPhone());
                    System.out.println("회원 지점: " + identifiedMember.getCenter());
                    System.out.println("회원 성별: " + identifiedMember.isMemberGender());
                    System.out.println("회원 나이: " + identifiedMember.getMemberAge());
                    System.out.println("이용 기간: " + identifiedMember.getDuration());
                    System.out.println("지정 강사번호: " + identifiedMember.getInstructorNo());
                    System.out.println("=====================================");
                    break;
                case 3:
                    System.out.println("3개월 이용권을 선택하셨습니다.");
                    identifiedMember.setDuration(identifiedMember.getDuration() + 3);
                    //내 정보 출력
                    System.out.println("=====================================");
                    System.out.println("회원 이름: " + identifiedMember.getMemberName());
                    System.out.println("회원 전화번호: " + identifiedMember.getMemberPhone());
                    System.out.println("회원 지점: " + identifiedMember.getCenter());
                    System.out.println("회원 성별: " + identifiedMember.isMemberGender());
                    System.out.println("회원 나이: " + identifiedMember.getMemberAge());
                    System.out.println("이용 기간: " + identifiedMember.getDuration());
                    System.out.println("지정 강사번호: " + identifiedMember.getInstructorNo());
                    System.out.println("=====================================");
                    break;
                case 6:
                    System.out.println("6개월 이용권을 선택하셨습니다.");
                    identifiedMember.setDuration(identifiedMember.getDuration() + 6);
                    //내 정보 출력
                    System.out.println("=====================================");
                    System.out.println("회원 이름: " + identifiedMember.getMemberName());
                    System.out.println("회원 전화번호: " + identifiedMember.getMemberPhone());
                    System.out.println("회원 지점: " + identifiedMember.getCenter());
                    System.out.println("회원 성별: " + identifiedMember.isMemberGender());
                    System.out.println("회원 나이: " + identifiedMember.getMemberAge());
                    System.out.println("이용 기간: " + identifiedMember.getDuration());
                    System.out.println("지정 강사번호: " + identifiedMember.getInstructorNo());
                    System.out.println("=====================================");
                    break;
                case 9:
                    System.out.println("이전으로 돌아갑니다.");
                    return;
                default:
                    System.out.println("다시 선택해주세요");
            }
            break;
        }

//        productDTO.setProductQuantity(productDTO.getProductQuantity() - 1); //물품 재고 -1
        // === 등급 선택 후 해당하는 강사 출력 ===

        // TODO:회원이입력한 등급과 동일한 강사 목록 출력
        repository.getGradeInstriuctorList('a');
        // TODO: 선택한 강사번호를 ClientDTO에 추가
        if (identifiedMember.getInstructorNo() == 0) { //강사 번호가 0이면서 지정되어있지 않으면
            while (true) {
                System.out.println("""
                        원하시는 강사의 번호를 입력해주세요 (5번까지)
                        이전으로 돌아시려면 ' 9 '를 입력해주세요
                        """);
                int instructorNumber;
                try {
                    instructorNumber = Integer.parseInt(sc.nextLine());
                    if (instructorNumber == 9) {
                        return;
                    }
                    if (0 < instructorNumber && instructorNumber <= repository.getInstructorListSize()) {
                        System.out.println("=====" + instructorNumber + "번 강사를 선택하셨습니다.=====");
                        identifiedMember.setInstructorNo(instructorNumber);
                        //내 정보 출력
                        System.out.println("=====================================");
                        System.out.println("회원 이름: " + identifiedMember.getMemberName());
                        System.out.println("회원 전화번호: " + identifiedMember.getMemberPhone());
                        System.out.println("회원 지점: " + identifiedMember.getCenter());
                        System.out.println("회원 성별: " + identifiedMember.isMemberGender());
                        System.out.println("회원 나이: " + identifiedMember.getMemberAge());
                        System.out.println("이용 기간: " + identifiedMember.getDuration());
                        System.out.println("지정 강사번호: " + identifiedMember.getInstructorNo());
                        System.out.println("=====================================");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("올바른 숫자 형식이 아닙니다. 다시 입력해주세요");
                    sc.nextLine();
                    continue;
                }
                break;
            }
        }
    }

    public void printClientInfo(String phoneNumber){
        for (ClientDTO clientDTO : repository.getClientListInfoList()){
            if(phoneNumber.equals(clientDTO.getMemberPhone())){
                System.out.println(clientDTO.toString());
                return;
            }
        }
    }
}
