package itss.nhom7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itss.nhom7.model.CardModel;
import itss.nhom7.service.impl.CardService;

@RestController
@RequestMapping(value="/card")
public class CardController {
	@Autowired
	private CardService cardService;
	
	@GetMapping(value="/getListCard/{userId}")
	public ResponseEntity<List<CardModel>> getListCardByUserId(@PathVariable("userId") int userId){
		return new ResponseEntity<List<CardModel>>(cardService.getListCardByUserId(userId),HttpStatus.OK);
	}
	
	@PostMapping(value="/addCard",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> addCard(CardModel cardModel){
		cardService.addCard(cardModel);
		return new ResponseEntity<String>("Add a new card successfully!",HttpStatus.OK);
	}
	@DeleteMapping(value="/deleteCard/{idCard}")
	public ResponseEntity<String> deleteCard(@PathVariable("idCard") int idCard){
		cardService.deleteCard(idCard);
		return new ResponseEntity<String>("Delete a new card successfully!",HttpStatus.OK);
	}
	
}
