package ru.strelchm.hhchallenge.interview;

import java.util.Scanner;

public class Third {
//    Представьте, что вас попросили спроектировать сервис для поиска работы, заказчик хочет, чтобы:
//
//            - работодатели могли размещать объявления о поиске сотрудников;
//- работодатели могли искать интересных сотрудников в списке резюме
//- работодатели могли связываться с интересными кандидатами
//- соискатели могли размещать свои резюме
//- соискатели могли искать интересные им вакансии
//- соискатели могли бы связываться с работодателями по интересующей их вакансии
//
//    Продумайте ответы на следующие вопросы:
//
//            - какие сущности будут нужны
//- какие связи будут между сущностями
//- какие методы будем реализовывать для каждой сущности
//- как бы мы могли реализовать поиск (по резюме или по вакансиям)
//
//    В рамках данной задачи не требуется писать код, но псевдокод можно использовать для иллюстрации выбранного подхода

//    Organization --> Employer
//    Employer --> Vacansy
//    Resume {
//        experience,
//        totalYears,
//        technologies
//    } <-- JobFinder
//    WorkingExperience {
//        organization,
//        technologies,
//        int experience
//    } <-- Resume
//    Resume <--> Technology
//    ChatRoom -- Employee
//    ChatRoom -- JobFinder
//
//    Service ChatRoomService
//    Service VacancyService
//    Service OrganizationService
//    Service ResumeService
}
