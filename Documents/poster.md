# Front End

- Allows the user to search for classes with a free-form text search
- Returns matching results from course names and numbers, subject, and
  professor names
- Allows the student to select a group of classes they are interested
  in (in progress)
- Provides a scheduling interface for taking that list of classes and
  creating multiple different schedules (in progress)

# Back End

- Provides data and full-text search capabilities to the front-end
- Downloads full course data for a specified quarter from the API
- Once data has been fully synched, the app is fully usable offline

# Server-side

- Provides a JSON API for all the data available on the original
  Classfinder website
- The data is extracted by scraping the broken HTML returned by
  Classfinder
