package com.example.springforum.dto.breadcrumb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BreadcrumbItem {
    String text;
    String url;
}
