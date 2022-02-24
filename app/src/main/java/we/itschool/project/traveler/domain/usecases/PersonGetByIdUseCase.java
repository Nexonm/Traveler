package we.itschool.project.traveler.domain.usecases;

import we.itschool.project.traveler.domain.entity.Person;
import we.itschool.project.traveler.domain.repository.PersonDomainRepository;

public class PersonGetByIdUseCase {

    private final PersonDomainRepository repository;

    public PersonGetByIdUseCase(PersonDomainRepository repository)   {
        this.repository = repository;
    }

    Person personGetById(int id) {
        return repository.personGetById(id);
    }

}
