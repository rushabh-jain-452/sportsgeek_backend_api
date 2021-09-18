package com.project.sportsgeek.service;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Gender;
import com.project.sportsgeek.repository.genderrepo.GenderRepository;
import com.project.sportsgeek.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderService {

    @Autowired
    @Qualifier("genderRepo")
    GenderRepository genderRepository;

    public Result<List<Gender>> findAllGender(){
        List<Gender> genderList = genderRepository.findAllGender();
        return new Result<>(200, genderList);
    }

    public Result<Gender> findGenderById(int genderId) throws Exception {
        Gender gender = genderRepository.findGenderById(genderId);
        if (gender != null) {
            return new Result<>(200, gender);
        }
        throw new ResultException(new Result<>(404, "Gender with GenderId: " + genderId + " not found."));
    }

    public Result<Gender> addGender(Gender gender) throws Exception {
        int genderId = genderRepository.addGender(gender);
        if (genderId > 0) {
            gender.setGenderId(genderId);
            return new Result<>(201, gender);
        }
        throw new ResultException(new Result<>(400, "Unable to add Gender."));
    }

    public Result<Gender> updateGender(int genderId, Gender gender) throws Exception {
        if (genderRepository.updateGender(genderId, gender)) {
            return new Result<>(200, gender);
        }
        throw new ResultException(new Result<>(404, "Gender with GenderId: " + genderId + " not found."));
    }

    public Result<String> deleteGender(int genderId) throws Exception {
        if (genderRepository.deleteGender(genderId)) {
            return new Result<>(200, "Gender deleted successfully.");
        }
        throw new ResultException(new Result<>(404, "Gender with GenderId: " + genderId + " not found."));
    }
}
