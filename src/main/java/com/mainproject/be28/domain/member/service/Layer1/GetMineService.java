package com.mainproject.be28.domain.member.service.Layer1;

import com.mainproject.be28.domain.notice.comment.entity.Comment;
import com.mainproject.be28.domain.notice.comment.service.GetMineCommentService;
import com.mainproject.be28.domain.shopping.complain.entity.Complain;
import com.mainproject.be28.domain.shopping.complain.service.layer2.GetMineComplainService;
import com.mainproject.be28.domain.shopping.review.entity.Review;
import com.mainproject.be28.domain.shopping.review.service.Layer2.GetMineReviewService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
@Service
@Transactional
public class GetMineService {
    private final Map<Class<?>, BiFunction<Integer, Integer, Page<?>>> serviceMap;

    public GetMineService(GetMineCommentService commentService,
                          GetMineComplainService complainService,
                          GetMineReviewService reviewService) {
        this.serviceMap = new HashMap<>();
        this.serviceMap.put(Comment.class, commentService::getMine);
        this.serviceMap.put(Complain.class, complainService::getMine);
        this.serviceMap.put(Review.class, reviewService::getMine);
    }

    public Page<?> getMine(int page, int size, Class<?> type) {
        return serviceMap.getOrDefault(type,
                        (p, s) -> Page.empty())
                .apply(page, size);
    }
}