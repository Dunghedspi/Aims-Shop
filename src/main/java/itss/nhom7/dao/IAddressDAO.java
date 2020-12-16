package itss.nhom7.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itss.nhom7.entities.Address;

@Transactional
@Repository
public interface IAddressDAO extends JpaRepository<Address, Integer>{
	Address findByDistrictAndVillageAndStreet(String district, String village, String street);
}
