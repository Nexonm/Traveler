package we.itschool.project.traveler.domain.usecases;

import we.itschool.project.traveler.domain.entity.Person;
import we.itschool.project.traveler.domain.repository.PersonDomainRepository;

public class PersonEditByIdUseCase {

    private final PersonDomainRepository repository;

    public PersonEditByIdUseCase(PersonDomainRepository repository) {
        this.repository = repository;
    }

    public void cardEditById(Person person) {
        repository.personEditById(person);
    }

}
