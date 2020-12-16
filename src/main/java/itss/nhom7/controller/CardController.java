package itss.nhom7.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itss.nhom7.model.CardModel;
import itss.nhom7.service.impl.CardService;

@RestController
@RequestMapping(value="/api/card")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CardController {
	@Autowired
	private CardService cardService;
	
	@GetMapping(value="/getListCard/{userId}")
	public ResponseEntity<Object> getListCardByUserId(@PathVariable("userId") int userId){
		HttpStatus httpStatus = null;
		List<CardModel> cardModels = new ArrayList<CardModel>();
		try {
			cardModels = cardService.getListCardByUserId(userId);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		
		return new ResponseEntity<Object>(cardModels,httpStatus);
	}
	
	@PostMapping(value="/addCard",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> addCard(CardModel cardModel){
		
		HttpStatus httpStatus = null;
		try {
			cardService.addCard(cardModel);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		
		return new ResponseEntity<String>(httpStatus);
	}
	@DeleteMapping(value="/deleteCard/{idCard}")
	public ResponseEntity<String> deleteCard(@PathVariable("idCard") int idCard){
		HttpStatus httpStatus = null;
		try {
			cardService.deleteCard(idCard);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		
		return new ResponseEntity<String>(httpStatus);
	}
	
}
