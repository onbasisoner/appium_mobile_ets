@HotelSearchTests
Feature: Hotel search and price verification in ETSTUR app

  @pricetests
  Scenario: Search for Istanbul hotels and verify price consistency
    * wait for "etstur_logo" to be visible
    * tap the "hotel_search"
    * wait for "where_to_go_text" to be visible
    * enter "İstanbul Otelleri" into the "hotel_search_textbox" field
    * wait for "istanbul_hotels" to be visible
    * tap the "istanbul_hotels"
    * tap the "entry_date"
    * wait for "calendar_title" to be visible
    * select the date from "day_choice" that is 1 days from today
    * select the date from "day_choice" that is 2 days from today
    * tap the "apply_button"
    * tap the "person_count"
    * wait for "person_count_page_title" to be visible
    * select 1 passenger from "decrease_person_adult"
    * tap the "person_apply_button"
    * wait for "search_hotel_button" to be visible
    * tap the "search_hotel_button"
    * wait for "hotels_list_title" to be visible
    * Save "first_hotel_price" to "the hotel price"
    * tap the "first_hotel"
    * wait for "see_rooms_button" to be visible
    * Save "hotel_detail_price" to "the detail price"
    * assert saved "the hotel price" is equal to this "the detail price"

