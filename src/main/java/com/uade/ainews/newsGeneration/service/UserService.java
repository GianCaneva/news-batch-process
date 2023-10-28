package com.uade.ainews.newsGeneration.service;

import com.uade.ainews.newsGeneration.dto.User;
import com.uade.ainews.newsGeneration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public static final int DECREMENT_VALUE = 15;
    public static final int MIN_INTEREST_VALUE = 20;
    @Autowired
    private UserRepository userRepository;

    // Process that discount user interests by section after a period of time
    public void reduceUserInterest() {
        List<User> all = userRepository.findAll();

        for (int i = 0; i < all.size(); i++) {
            User user = all.get(i);
            Integer politicsInterest = user.getPoliticsInterest();
            if (politicsInterest >= MIN_INTEREST_VALUE) {
                user.setPoliticsInterest(politicsInterest - DECREMENT_VALUE);
            }
            Integer economyInterest = user.getEconomyInterest();
            if (economyInterest >= MIN_INTEREST_VALUE) {
                user.setEconomyInterest(economyInterest - DECREMENT_VALUE);
            }
            Integer sportsInterest = user.getSportsInterest();
            if (sportsInterest >= MIN_INTEREST_VALUE) {
                user.setSportsInterest(sportsInterest - DECREMENT_VALUE);
            }
            Integer socialInterest = user.getSocialInterest();
            if (socialInterest >= MIN_INTEREST_VALUE) {
                user.setSocialInterest(socialInterest - DECREMENT_VALUE);
            }
            Integer internationalInterest = user.getInternationalInterest();
            if (internationalInterest >= MIN_INTEREST_VALUE) {
                user.setInternationalInterest(internationalInterest - DECREMENT_VALUE);
            }
            Integer policeInterest = user.getPoliceInterest();
            if (policeInterest >= MIN_INTEREST_VALUE) {
                user.setPoliceInterest(policeInterest - DECREMENT_VALUE);
            }
            userRepository.save(user);
        }

    }
}
