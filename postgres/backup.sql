--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5 (Debian 17.5-1.pgdg120+1)
-- Dumped by pg_dump version 17.5 (Debian 17.5-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: word_search_similar_words; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.word_search_similar_words (
    word_search_id bigint NOT NULL,
    name character varying(255),
    place integer,
    similarity_score double precision
);


ALTER TABLE public.word_search_similar_words OWNER TO postgres;

--
-- Name: word_searchs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.word_searchs (
    id bigint NOT NULL,
    nb_found integer NOT NULL,
    ref character varying(255) NOT NULL,
    word_id bigint
);


ALTER TABLE public.word_searchs OWNER TO postgres;

--
-- Name: word_searchs_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.word_searchs_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.word_searchs_seq OWNER TO postgres;

--
-- Name: words; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.words (
    id bigint NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.words OWNER TO postgres;

--
-- Name: words_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.words_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.words_seq OWNER TO postgres;

--
-- Data for Name: word_search_similar_words; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.word_search_similar_words (word_search_id, name, place, similarity_score) FROM stdin;
\.


--
-- Data for Name: word_searchs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.word_searchs (id, nb_found, ref, word_id) FROM stdin;
\.


--
-- Data for Name: words; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.words (id, name) FROM stdin;
\.


--
-- Name: word_searchs_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.word_searchs_seq', 1, false);


--
-- Name: words_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.words_seq', 1, false);


--
-- Name: words ukklst28o1053qakfixvpay80bn; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.words
    ADD CONSTRAINT ukklst28o1053qakfixvpay80bn UNIQUE (name);


--
-- Name: word_searchs ukrfjbo6ceo3s1aw7d5kdkoxa6h; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.word_searchs
    ADD CONSTRAINT ukrfjbo6ceo3s1aw7d5kdkoxa6h UNIQUE (ref);


--
-- Name: word_searchs word_searchs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.word_searchs
    ADD CONSTRAINT word_searchs_pkey PRIMARY KEY (id);


--
-- Name: words words_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.words
    ADD CONSTRAINT words_pkey PRIMARY KEY (id);


--
-- Name: idx_word_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_word_name ON public.words USING btree (name);


--
-- Name: idx_word_search_ref; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_word_search_ref ON public.word_searchs USING btree (ref);


--
-- Name: word_searchs fkb2oxb5318lql2hhm51gg3xpad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.word_searchs
    ADD CONSTRAINT fkb2oxb5318lql2hhm51gg3xpad FOREIGN KEY (word_id) REFERENCES public.words(id);


--
-- Name: word_search_similar_words fkeuar2rxdlwp27v41v9t3p746o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.word_search_similar_words
    ADD CONSTRAINT fkeuar2rxdlwp27v41v9t3p746o FOREIGN KEY (word_search_id) REFERENCES public.word_searchs(id);


--
-- PostgreSQL database dump complete
--

