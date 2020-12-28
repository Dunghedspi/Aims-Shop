package itss.nhom7.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import itss.nhom7.entities.HistoryCrudProduct;

@ResponseBody
@Transactional
public interface IHistoryCrudProduct extends JpaRepository<HistoryCrudProduct, Integer>{

	HistoryCrudProduct getByProductIdAndContent(int productId, String content);
}
