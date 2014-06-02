# Classfinder 2.0

This application provides a mobile interface to Western’s class
registration system. Although the official Classfinder web application
was once a technological masterpiece, it has not been updated
significantly in the past decade.

The recently added scheduling system is difficult to use, and requires
a very specific pattern of usage. Will it is slightly more effective
than planning on paper, we feel that more can be done to ease the
burden of creating a schedule each quarter.

# Main Goals

- Provide a more modern user interface
- Provide a more robust searching interface (drop downs are lame)
- Provide an easier to use scheduling interface, that is better
  integrated with the class searching


# Architecture

## Front End

- Allows the user to search for classes with a free-form text search
- Returns matching results from course names and numbers, subject, and
  professor names
- Allows the student to select a group of classes they are interested
  in (in progress)
- Provides a scheduling interface for taking that list of classes and
  creating multiple different schedules (in progress)

## Back End

- Provides data and full-text search capabilities to the front-end
- Downloads full course data for a specified quarter from the API
- Once data has been fully synched, the app is fully usable offline

## Server-side

- Provides a JSON API for all the data available on the original
  Classfinder website
- The data is extracted by scraping the broken HTML returned by
  Classfinder


# Final Product

We made significant progress on this app. On the front end, the main
interface elements are all in place.  A set of custom view elements is
still needed to implement the envisioned scheduling mechanism. The
back-end infrastructure is still not fully functional, and it also
needs to be hooked up to the front-end. The server-side API provides a
basic wrapper around the classfinder site, however more work remains
to be done to provide the data in a cleaner form.
