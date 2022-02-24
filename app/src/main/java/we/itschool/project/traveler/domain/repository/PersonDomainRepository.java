package we.itschool.project.traveler.domain.repository;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import we.itschool.project.traveler.domain.entity.Card;
import we.itschool.project.traveler.domain.entity.Person;

public interface PersonDomainRepository {

    void personAddNew(Person person);

    void personDeleteById(Person person);

    void personEditById(Person person);

    MutableLiveData<ArrayList<Person>> personGetAll();

    Person personGetById(int id);

}
