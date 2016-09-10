create table if not exists ssc_relatedness_values(
	semantic_relatedness_values_pk IDENTITY NOT NULL,
	first_sentence_id VARCHAR2(50) NOT NULL,
	second_sentence_id VARCHAR2(50) NOT NULL,
	simple_feature_value DOUBLE NOT NULL,
	lesk_feature_value DOUBLE NOT NULL,
	lin_feature_value DOUBLE NOT NULL,
	path_feature_value DOUBLE NOT NULL,
	matched_word_count INT NOT NULL,
	mismatched_word_count INT NOT NULL,
	minimum_word_count INT NOT NULL,
	PRIMARY KEY(semantic_relatedness_values_pk)
);

create table if not exists sentence_details(
	sentence_details_pk IDENTITY NOT NULL,
	sentence_id VARCHAR2(50) NOT NULL,
	sentence_content VARCHAR2(200) NOT NULL,
	sentence_content_words ARRAY NOT NULL
	sentence_content_word_count INT NOT NULL,
	PRIMARY KEY(sentence_details_pk)
);