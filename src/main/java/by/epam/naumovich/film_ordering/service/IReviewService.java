package by.epam.naumovich.film_ordering.service;

import java.util.List;

import by.epam.naumovich.film_ordering.bean.Review;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Defines methods that receive parameters from Command implementations, verify them, construct necessary entities if needed 
 * and then pass them to the DAO layer, possibly getting some objects or primitive values back and passing them further back to the commands.
 * These methods operate with the Review entity.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public interface IReviewService {
	
	/**
	 * Constructs a new review entity based on input parameters received from the Controller layer, verifies them and either 
	 * passes to the DAO layer or throws an exception 
	 * 
	 * @param userId review user ID
	 * @param filmId review film ID
	 * @param mark review mark
	 * @param type review type
	 * @param text review text
	 * @throws ServiceException
	 */
	void create(int userId, int filmId, String mark, String type, String text) throws ServiceException;
	
	/**
	 * Verifies the input parameter and either passes it to the DAO layer or throws an exception
	 * 
	 * @param userId review user ID
	 * @param filmId review film ID
	 * @throws ServiceException
	 */
	void delete(int userId, int filmId) throws ServiceException;
	
	/**
	 * Verifies input parameter and passes it to the DAO layer, received a set of found reviews back and returns it to the Controller layer
	 * or throws an exception if it is empty
	 * 
	 * @param id ID of the user whose reviews are searched
	 * @return a set of found reviews
	 * @throws ServiceException
	 */
	List<Review> getAllByUserId(int id) throws ServiceException;
	
	/**
	 * Verifies input parameter and passes it to the DAO layer, received a particular set of found reviews back and returns it to the Controller layer
	 * or throws an exception if it is empty
	 * 
	 * @param userId ID of the user whose reviews are searched
	 * @param pageNum number of page
	 * @return a set of found reviews
	 * @throws ServiceException
	 */
	List<Review> getAllPartByUserId(int userId, int pageNum) throws ServiceException;

	/**
	 * Verifies input parameter and passes it to the DAO layer, received a set of found reviews back and returns it to the Controller layer
	 * or throws an exception if it is empty
	 * 
	 * @param id ID of the film which reviews are searched
	 * @return a set of found reviews
	 * @throws ServiceException
	 */
	List<Review> getAllByFilmId(int id) throws ServiceException;
	
	/**
	 * Verifies input parameter and passes it to the DAO layer, received a particular set of found reviews back and returns it to the Controller layer
	 * or throws an exception if it is empty
	 * 
	 * @param filmId ID of the film which reviews are searched
	 * @param pageNum number of page
	 * @return a set of found reviews
	 * @throws ServiceException
	 */
	List<Review> getAllPartByFilmId(int filmId, int pageNum) throws ServiceException;
	
	/**
	 * Verifies the input parameters and passes them to the DAO layer, receives the review entity, returns it back to the Controller layer
	 * or throws an exception if it equals null
	 * 
	 * @param userId ID of the user whose review is searched
	 * @param filmId ID of the film which review is searched
	 * @return found review entity
	 * @throws ServiceException
	 */
	Review getByUserAndFilmId(int userId, int filmId)  throws ServiceException;
	
	/**
	 * Receives a particular set of all reviews from the DAO layer depending on the current page
	 * and passes it back to the Controller layer or throws an exception if it is empty
	 * 
	 * @return a set of reviews
	 * @throws ServiceException
	 */
	List<Review> getAllPart(int pageNum) throws ServiceException;
	
	/**
	 * Counts the number of pages needed to locate all reviews within the pagination.
	 * 
	 * @return number of pages
	 */
	int countPages();
	
	/**
	 * Counts the number of pages needed to locate all user reviews within the pagination.
	 * 
	 * @param userId ID of the user whose reviews are searched
	 * @return number of pages
	 * @throws ServiceException
	 */
	int countByUserId(int userId) throws ServiceException;
	
	/**
	 * Counts the number of pages needed to locate all film reviews within the pagination.
	 * 
	 * @param filmId ID of the film which reviews are searched
	 * @return number of pages
	 * @throws ServiceException
	 */
	int countByFilmId(int filmId) throws ServiceException;
}
