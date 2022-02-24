package we.itschool.project.traveler.domain.usecases;

import we.itschool.project.traveler.domain.entity.Person;
import we.itschool.project.traveler.domain.repository.PersonDomainRepository;

public class PersonDeleteByIdUseCase {

    private final PersonDomainRepository repository;

    public PersonDeleteByIdUseCase(PersonDomainRepository repository) {
        this.repository = repository;
    }

    public void cardDeleteById(Person person) {
        repository.personDeleteById(person);
    }

}
