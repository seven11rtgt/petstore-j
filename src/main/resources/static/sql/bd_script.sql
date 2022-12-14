--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4
-- Dumped by pg_dump version 14.4

-- Started on 2022-12-13 16:14:48

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
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
-- TOC entry 210 (class 1259 OID 41512)
-- Name: cart; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cart (
    id bigint NOT NULL,
    person_id bigint,
    product_id bigint
);


ALTER TABLE public.cart OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 41511)
-- Name: cart_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cart_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cart_id_seq OWNER TO postgres;

--
-- TOC entry 3372 (class 0 OID 0)
-- Dependencies: 209
-- Name: cart_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cart_id_seq OWNED BY public.cart.id;


--
-- TOC entry 212 (class 1259 OID 41519)
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    id integer NOT NULL,
    name character varying(255)
);


ALTER TABLE public.category OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 41518)
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.category_id_seq OWNER TO postgres;

--
-- TOC entry 3373 (class 0 OID 0)
-- Dependencies: 211
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.category_id_seq OWNED BY public.category.id;


--
-- TOC entry 214 (class 1259 OID 41526)
-- Name: image; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.image (
    id bigint NOT NULL,
    file_name character varying(255),
    product_id bigint NOT NULL
);


ALTER TABLE public.image OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 41525)
-- Name: image_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.image_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.image_id_seq OWNER TO postgres;

--
-- TOC entry 3374 (class 0 OID 0)
-- Dependencies: 213
-- Name: image_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.image_id_seq OWNED BY public.image.id;


--
-- TOC entry 220 (class 1259 OID 41573)
-- Name: order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."order" (
    id bigint NOT NULL,
    order_number character varying(10) NOT NULL,
    quantity integer NOT NULL,
    price double precision NOT NULL,
    order_date timestamp without time zone,
    status character varying(20) NOT NULL,
    customer_id bigint,
    product_id bigint NOT NULL
);


ALTER TABLE public."order" OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 41572)
-- Name: order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."order" ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1
);


--
-- TOC entry 216 (class 1259 OID 41533)
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    id bigint NOT NULL,
    login character varying(50),
    password character varying(100),
    role character varying(255)
);


ALTER TABLE public.person OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 41532)
-- Name: person_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.person_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.person_id_seq OWNER TO postgres;

--
-- TOC entry 3375 (class 0 OID 0)
-- Dependencies: 215
-- Name: person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.person_id_seq OWNED BY public.person.id;


--
-- TOC entry 218 (class 1259 OID 41540)
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    id bigint NOT NULL,
    date_time timestamp(6) without time zone,
    description character varying(2000),
    price real NOT NULL,
    quantity integer NOT NULL,
    seller character varying(255),
    title character varying(255),
    warehouse character varying(255),
    category_id integer NOT NULL,
    CONSTRAINT product_price_check CHECK ((price >= (1)::double precision)),
    CONSTRAINT product_quantity_check CHECK ((quantity >= 1))
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 41539)
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_id_seq OWNER TO postgres;

--
-- TOC entry 3376 (class 0 OID 0)
-- Dependencies: 217
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_id_seq OWNED BY public.product.id;


--
-- TOC entry 3189 (class 2604 OID 41515)
-- Name: cart id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart ALTER COLUMN id SET DEFAULT nextval('public.cart_id_seq'::regclass);


--
-- TOC entry 3190 (class 2604 OID 41522)
-- Name: category id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category ALTER COLUMN id SET DEFAULT nextval('public.category_id_seq'::regclass);


--
-- TOC entry 3191 (class 2604 OID 41529)
-- Name: image id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.image ALTER COLUMN id SET DEFAULT nextval('public.image_id_seq'::regclass);


--
-- TOC entry 3192 (class 2604 OID 41536)
-- Name: person id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);


--
-- TOC entry 3193 (class 2604 OID 41543)
-- Name: product id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product ALTER COLUMN id SET DEFAULT nextval('public.product_id_seq'::regclass);


--
-- TOC entry 3356 (class 0 OID 41512)
-- Dependencies: 210
-- Data for Name: cart; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3358 (class 0 OID 41519)
-- Dependencies: 212
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.category (id, name) VALUES (1, '??????????????');
INSERT INTO public.category (id, name) VALUES (2, '??????????');
INSERT INTO public.category (id, name) VALUES (3, '?????????????????? ?? ????????');


--
-- TOC entry 3360 (class 0 OID 41526)
-- Dependencies: 214
-- Data for Name: image; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.image (id, file_name, product_id) VALUES (1, '1e0ff40a-d7ef-47b1-9712-7d3bb2218b4b', 1);
INSERT INTO public.image (id, file_name, product_id) VALUES (2, 'bb08b8a1-9ca3-4881-a003-f96bbaff8c93', 1);
INSERT INTO public.image (id, file_name, product_id) VALUES (3, '46fb7554-b573-4ad7-b9a0-5818bf1cb639', 1);
INSERT INTO public.image (id, file_name, product_id) VALUES (6, '30915210-1377-4df0-bd10-2496d3d5a804', 2);
INSERT INTO public.image (id, file_name, product_id) VALUES (7, '007ef0e8-e1e1-4d4e-98c9-8c4dba3bc5e4', 2);
INSERT INTO public.image (id, file_name, product_id) VALUES (8, 'db5442b9-fd94-48cd-b437-3a3b9a0c0b7e', 2);


--
-- TOC entry 3366 (class 0 OID 41573)
-- Dependencies: 220
-- Data for Name: order; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3362 (class 0 OID 41533)
-- Dependencies: 216
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.person (id, login, password, role) VALUES (1, 'admin', '$2a$10$xsv4inPgu5rTl88LwGox4e7SfYPFOOkPTMc0LbNNP/70po9Kz7f1i', 'ROLE_ADMIN');


--
-- TOC entry 3364 (class 0 OID 41540)
-- Dependencies: 218
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.product (id, date_time, description, price, quantity, seller, title, warehouse, category_id) VALUES (2, '2022-12-13 00:29:57.854166', '???????????????????????????? ?????????????????????? ???????? ?????? ?????????? ?????? ?????????????????????? ???????????????????? ????????????, ?????????????????????? ???????????? ?????????????? ?????????? ?????????? ?? ???????????????? ?????????????????? ?????????????????????? ????????????????????', 2150, 100, '????????????????????????????', '?????????? ???????? ?????? ?????????? Farmina Vet Life, ?????? ?????????????? ?????? 2 ????', '?????????? 2', 2);
INSERT INTO public.product (id, date_time, description, price, quantity, seller, title, warehouse, category_id) VALUES (1, NULL, '??????????????????????, ?????????? ???????????????????? "???????? ??????" ?? ?????????????????? ?????? ?????????? ???? ???????????????????? ????????, ???? ???? ?????????? ?????? ???????????????? ?????? ?????????? ????????-???? ?????????? ?? ????????????, ?????? ???? ?????????????????????????? ??????????????????????, ???? ?????????????? ???? ?? ???????????????????? ???????? ????????, ???????? ???????? ???? ???????????????????????? ???? ???????????? ??? ???????????????????????????? ?????????????? ???? ?????????? ?? ?????????? ????????????. "???????? ??????" ?????????????????????????? ???? ???????? ????????????????, ???????????????????????????? ???????????? ???????????????????? ???????????????? ?? ???????? ?????????????? ?????????? ???? ??????????????????, ?????? ?????????????????? ??????????????????.
???? ???????? ?????????? ???? ?????????????????? ?????????????????????? ?? ?????????? ???????????????????? ??????????????.', 470, 15, '??????????????????', '?????????????? ?????? ?????????? ?????????????????????????? HEXBUG', '?????????? 1', 1);


--
-- TOC entry 3377 (class 0 OID 0)
-- Dependencies: 209
-- Name: cart_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cart_id_seq', 1, false);


--
-- TOC entry 3378 (class 0 OID 0)
-- Dependencies: 211
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_id_seq', 1, false);


--
-- TOC entry 3379 (class 0 OID 0)
-- Dependencies: 213
-- Name: image_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.image_id_seq', 8, true);


--
-- TOC entry 3380 (class 0 OID 0)
-- Dependencies: 219
-- Name: order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_id_seq', 1, false);


--
-- TOC entry 3381 (class 0 OID 0)
-- Dependencies: 215
-- Name: person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_id_seq', 1, true);


--
-- TOC entry 3382 (class 0 OID 0)
-- Dependencies: 217
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_id_seq', 2, true);


--
-- TOC entry 3197 (class 2606 OID 41517)
-- Name: cart cart_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_pkey PRIMARY KEY (id);


--
-- TOC entry 3199 (class 2606 OID 41524)
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- TOC entry 3201 (class 2606 OID 41531)
-- Name: image image_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT image_pkey PRIMARY KEY (id);


--
-- TOC entry 3209 (class 2606 OID 41577)
-- Name: order order_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order_pkey PRIMARY KEY (id);


--
-- TOC entry 3203 (class 2606 OID 41538)
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- TOC entry 3205 (class 2606 OID 41549)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 3207 (class 2606 OID 41551)
-- Name: product uk_qka6vxqdy1dprtqnx9trdd47c; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT uk_qka6vxqdy1dprtqnx9trdd47c UNIQUE (title);


--
-- TOC entry 3213 (class 2606 OID 41567)
-- Name: product fk1mtsbur82frn64de7balymq9s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk1mtsbur82frn64de7balymq9s FOREIGN KEY (category_id) REFERENCES public.category(id);


--
-- TOC entry 3211 (class 2606 OID 41557)
-- Name: cart fk3d704slv66tw6x5hmbm6p2x3u; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT fk3d704slv66tw6x5hmbm6p2x3u FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- TOC entry 3212 (class 2606 OID 41562)
-- Name: image fkgpextbyee3uk9u6o2381m7ft1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT fkgpextbyee3uk9u6o2381m7ft1 FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- TOC entry 3210 (class 2606 OID 41552)
-- Name: cart fko3jsgdxo6ax3cwml53qyems2j; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT fko3jsgdxo6ax3cwml53qyems2j FOREIGN KEY (person_id) REFERENCES public.person(id);


--
-- TOC entry 3214 (class 2606 OID 41578)
-- Name: order order_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.person(id);


--
-- TOC entry 3215 (class 2606 OID 41584)
-- Name: order order_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.product(id);


-- Completed on 2022-12-13 16:14:50

--
-- PostgreSQL database dump complete
--

