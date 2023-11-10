

package org.iesvdm.kata1;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.*;


public class  Exercise4Test extends PetDomainForKata
{
    @Test
    @Tag("KATA")
    public void getAgeStatisticsOfPets()
    {
        //Assertions.fail("Refactor to stream. Don't forget to comment this out or delete it when you are done.");

        //TODO
        // Replace by stream of petAges
        var petAges = people.stream().map(Person::getPets).flatMap(Collection::stream).map(Pet::getAge).toList();

        var uniqueAges = Set.copyOf(petAges);

        var expectedSet = Set.of(1, 2, 3, 4);
        Assertions.assertEquals(expectedSet, uniqueAges);

        //TODO
        // Replace by stream
        // IntSummaryStatistics is a class in JDK 8 use it over petAges
        var stats = petAges.stream().collect(summarizingInt(Integer::intValue));

        //TODO
        // Replace 0 by stream over petAges
        Assertions.assertEquals(stats.getMin(), 1);
        Assertions.assertEquals(stats.getMax(), 4);
        Assertions.assertEquals(stats.getSum(), 17);
        Assertions.assertEquals(stats.getAverage(), 1.8, 1);
        Assertions.assertEquals(stats.getCount(), 9);



        //TODO
        // Replace by correct stream
        // All age > 0
        Assertions.assertTrue(petAges.stream().allMatch(i->i>0));
        //TODO
        // No one ages == 0
        Assertions.assertFalse(petAges.stream().anyMatch(i->i==0));
        //TODO
        // No one age < 0
        Assertions.assertTrue(petAges.stream().noneMatch(i->i<0));
    }

    @Test
    @Tag("KATA")
    @DisplayName("bobSmithsPetNamesAsString - 🐱 🐶")
    public void bobSmithsPetNamesAsString()
    {
        //Assertions.fail("Refactor to stream. Don't forget to comment this out or delete it when you are done.");

        //TODO
        // find Bob Smith
        Person person = people.stream().filter(p->p.getFullName().equals("Bob Smith")).findFirst().orElse(null);

        //TODO
        // get Bob Smith's pets' names
        String names = person.getPets().stream().map(Pet::getName).collect(Collectors.joining(" & "));
        Assertions.assertEquals("Dolly & Spot", names);
    }

    @Test
    @Tag("KATA")
    public void immutablePetCountsByEmoji()
    {
        //Assertions.fail("Refactor to stream. Don't forget to comment this out or delete it when you are done.");

        //TODO
        // Unmodificable map of counts
        Map<String, Long> countsByEmoji = people.stream()
                .flatMap(p->p.getPets().stream())
                .collect( groupingBy(p -> p.getType().toString(),
                                counting())
                        );

        Assertions.assertEquals(
                Map.of("🐱", 2L, "🐶", 2L, "🐹", 2L, "🐍", 1L, "🐢", 1L, "🐦", 1L),
                countsByEmoji
        );
    }

    /**
     * The purpose of this test is to determine the top 3 pet types.
     */
    @Test
    @Tag("KATA")
    @DisplayName("topThreePets - 🐱 🐶 🐹")
    public void topThreePets()
    {
        //Assertions.fail("Refactor to stream. Don't forget to comment this out or delete it when you are done.");

        //TODO
        // Obtain three top pets
        var favorites =  people.stream()
                .flatMap(p->p.getPets().stream())
                .collect( groupingBy(p -> p.getType().toString(),
                        counting()))
                .entrySet()
                .stream()
                .sorted(((o1, o2) -> (int)(o2.getValue()-o1.getValue())))
                .limit(3)
                        .toList();
        //está bien(creo, no se porque me da false, pero el contenido son iguales).
        System.out.println(favorites);
        Assertions.assertEquals(3, favorites.size());
        Assertions.assertTrue(favorites.contains(new AbstractMap.SimpleEntry<>(PetType.CAT, Long.valueOf(2))));
        Assertions.assertTrue(favorites.contains(new AbstractMap.SimpleEntry<>(PetType.DOG, Long.valueOf(2))));
        Assertions.assertTrue(favorites.contains(new AbstractMap.SimpleEntry<>(PetType.HAMSTER, Long.valueOf(2))));
    }

    @Test
    @Tag("KATA")
    public void getMedianOfPetAges()
    {
        //Assertions.fail("Refactor to stream. Don't forget to comment this out or delete it when you are done.");

        //TODO
        // Obtain pet ages
        var petAges = people.stream()
                .flatMap(p->p.getPets().stream()
                        .map(Pet::getAge))
                .toList();

        //TODO
        // sort pet ages
        var sortedPetAges = people.stream()
                .flatMap(p->p.getPets().stream()
                        .map(Pet::getAge))
                .sorted(Integer::compareTo)
                .toList();

        double median;

        if (0 == sortedPetAges.size() % 2)
        {
            //TODO
            //
            // The median of a list of even numbers is the average of the two middle items
            median = (double) (sortedPetAges.get(sortedPetAges.size() / 2) + sortedPetAges.get(sortedPetAges.size() / 2)) /2;
        }
        else
        {
            // The median of a list of odd numbers is the middle item
            median = sortedPetAges.get(abs(sortedPetAges.size() / 2)).doubleValue();
        }
        Assertions.assertEquals(2.0, median, 0.0);
    }
}
