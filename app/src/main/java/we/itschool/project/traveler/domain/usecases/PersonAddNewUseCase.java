package we.itschool.project.traveler.domain.usecases;

import we.itschool.project.traveler.domain.entity.Person;
import we.itschool.project.traveler.domain.repository.PersonDomainRepository;

public class PersonAddNewUseCase {

    private final PersonDomainRepository repository;

    public PersonAddNewUseCase(PersonDomainRepository repository) {
        this.repository = repository;
    }

    public void cardAddNew(Person person){
        repository.personAddNew(person);
    }
}
