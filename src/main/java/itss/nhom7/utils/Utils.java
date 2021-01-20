package itss.nhom7.utils;
 
import java.util.List;

import javax.servlet.http.HttpServletRequest;
 
import itss.nhom7.entities.CartDetail;
import itss.nhom7.model.MediaModel;

public class Utils {
	 
	public static void storeCheckoutSession(HttpServletRequest request,List<MediaModel> mediaModel) {
        request.getSession().setAttribute("checkout", mediaModel);
    }
 
    public static CartDetail getCheckoutSession(HttpServletRequest request) {
        return (CartDetail) request.getSession().getAttribute("checkout");
    }
    
    public static void storeTotalPriceSession(HttpServletRequest request, int total_price) {
        request.getSession().setAttribute("total_price", total_price );
    }
 
    public static int getTotalPriceSession(HttpServletRequest request) {
        return (int) request.getSession().getAttribute("total_price");
    }
}    