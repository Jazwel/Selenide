package ru.netology.stats;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

class Deliverycard {

        private String getFutureDate(int daysToAdd) {
            LocalDate currentDate = LocalDate.now();
            LocalDate futureDate = currentDate.plusDays(daysToAdd);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String formattedDate = futureDate.format(formatter);
            return formattedDate;

        }

        @Test
        void shouldCheck() {
            open("http://localhost:9999/");
            $("[data-test-id=city] input").setValue("Москва");
            $$(".popup__inner").last().click();
            $(".calendar-input__custom-control input").doubleClick().sendKeys(getFutureDate(3));
            $("[data-test-id=name] input").setValue("Иванов Иван");
            $("[data-test-id=phone] input").setValue("+79102222222");
            $("[data-test-id=agreement]").click();
            $(".button").click();
            $(".notification__content").shouldBe(visible, Duration.ofSeconds(15));
            $("[data-test-id=notification] .notification__content")
                    .shouldHave(exactText("Встреча успешно забронирована на " + getFutureDate(3)));
        }
    }

