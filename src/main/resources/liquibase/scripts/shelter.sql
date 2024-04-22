--liquibase formatted sql

--changeset kuznetsovanaya:insert_cat_shelter
insert into shelter (greeting, info, dating_rules, adoption_documents, transportation_recommendations,
                     recommendations_arranging_baby, recommendations_arranging_adult,
                     recommendations_arranging_with_features, type_of_animal)
values ('Вас приветствует приют Зверополис. Пожалуйста выберите интересующее Вас действие:',
        'Тут типо о местоположении',
        'А здесь про правила как взять животное',
        'А здесь про необходимые документы',
        'Тут пусть будут рекомендации по транспортировке',
        'Тут рекомендации по обустройству дома для щенка',
        'А здесь рекомендации по обустройству дома для взрослой собаки',
        'А тут по обустройству для собаки с особенностями',
        'CAT')

--changeset kuznetsovanaya:insert_dog_shelter
    insert into shelter (greeting, info, dating_rules, adoption_documents, transportation_recommendations,
                         recommendations_arranging_baby, recommendations_arranging_adult,
                         recommendations_arranging_with_features, type_of_animal)
values ('Вас приветствует приют Зверополис. Пожалуйста выберите интересующее Вас действие:',
    'Тут типо о местоположении',
    'А здесь про правила как взять животное',
    'А здесь про необходимые документы',
    'Тут пусть будут рекомендации по транспортировке',
    'Тут рекомендации по обустройству дома для щенка',
    'А здесь рекомендации по обустройству дома для взрослой собаки',
    'А тут по обустройству для собаки с особенностями',
    'DOG')