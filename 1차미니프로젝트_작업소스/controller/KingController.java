package com.eureka.king.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eureka.king.entity.King;
import com.eureka.king.repository.KingRepository;


@Controller
public class KingController {

	@Autowired
	private KingRepository kRepo;
	
	private long count = 0;
	
	
	@GetMapping("/king/intro")
	@ResponseBody
	public Map<String, Object> count(){
		Map<String, Object> result = new HashMap<>();
		//long count = kRepo.count();
		result.put("code", "ok");
		result.put("count", count);
		return result;		
	}
	
	@GetMapping("/king/rank")
	@ResponseBody
	public List<King> list(){
		return kRepo.findAll(Sort.by(Sort.Direction.DESC, "count", "nickname"));
		
	}
	
	
	
	
    @GetMapping("/king/game")
    @ResponseBody
    public Map<String, Object> game() {
        Map<String, Object> result = new HashMap<>();

        // 랜덤 숫자 두 개 생성
        int com_num1 = (int) (Math.random() * 100);
        int com_num2 = (int) (Math.random() * 100);

        // 결과 맵에 추가
        result.put("com_num1", com_num1);
        result.put("com_num2", com_num2);

        return result;
    }

    
    @GetMapping("/king/game/check")
    @ResponseBody
    public Map<String, Object> checkAnswer(
            @RequestParam("answer") int answer,
            @RequestParam("num1") int num1,
            @RequestParam("num2") int num2,
    		@RequestParam("count") long count){
        Map<String, Object> result = new HashMap<>();

        // 정답 확인
       
        if (answer == (num1 + num2)) {
            count++;

            // 새로운 범위로 랜덤 숫자 생성
            int newNum1 = (int) (Math.random() * 100);
            int newNum2 = (int) (Math.random() * 100);

            // 결과 맵 업데이트
            result.put("code", "ok");
            result.put("message", "정답! 당신이 왕?");
            result.put("com_num1", newNum1);
            result.put("com_num2", newNum2);
            result.put("count", count);
            
        } else {
            result.put("code", "error");
            result.put("message", "오답입니다. 다시 시도해주세요.");
        }

        return result;
    }
    
    @PostMapping("/king/save")
    @ResponseBody
    public Map<String, Object> saveResult(@RequestParam("nickname") String nickname,
    		@RequestParam("count") long count) {
        Map<String, Object> result = new HashMap<>();
        try {
            King king = new King();
            king.setNickname(nickname);
            king.setCount(count);
            kRepo.save(king);
            result.put("code", "ok");
            result.put("count", count);
            result.put("message", "닉네임과 점수가 저장되었습니다.");
        } catch (Exception e) {
            result.put("code", "error");
            result.put("message", "저장 중 오류가 발생했습니다.");
        }
        return result;
    }
}