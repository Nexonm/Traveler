package we.itschool.project.traveler.domain.usecases;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import we.itschool.project.traveler.domain.entity.Person;
import we.itschool.project.traveler.domain.repository.PersonDomainRepository;

public class PersonGetAllUseCase {

    private final PersonDomainRepository repository;

    public PersonGetAllUseCase(PersonDomainRepository repository) {
        this.repository = repository;
    }

    MutableLiveData<ArrayList<Person>> personGetAll() {
        return repository.personGetAll();
    }

}
