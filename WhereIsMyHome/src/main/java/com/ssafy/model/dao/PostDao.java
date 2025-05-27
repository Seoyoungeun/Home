
package com.ssafy.model.dao;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.ssafy.model.dto.Post;
@Mapper
public interface PostDao {
	int insert(Post post);
	int update(Post post);
	int delete(int pid);
	Post select(@Param("pid") int pid);
	List<Post> selectByCategory(String category);
	List<Post> searchByKeyword(String keyword);
	List<Post> findByUserId(@Param("uid") int uid);
	void incrementViewCount(int pid);
	List<Post> selectByCategoryWithPaging(
		    @Param("category") String category,
		    @Param("size") int size,
		    @Param("offset") int offset
		);
	int countByCategory(@Param("category") String category);
	
    List<Post> search(
            @Param("type") String type,
            @Param("keyword") String keyword,
            @Param("limit") int limit,
            @Param("offset") int offset
        );
}
